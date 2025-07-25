package com.skravetz.cn1grupo9.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skravetz.cn1grupo9.config.RabbitMQConfig;
import com.skravetz.cn1grupo9.dto.PriceChangeDTO;
import com.skravetz.cn1grupo9.entity.PriceChangeLog;
import com.skravetz.cn1grupo9.repository.PriceChangeLogRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceChangeConsumer {

    private final PriceChangeLogRepository priceChangeLogRepository;
    private final ObjectMapper objectMapper;

  @RabbitListener(queues = RabbitMQConfig.PRICE_CHANGE_QUEUE)
    public void processPriceChangeMessage(PriceChangeDTO priceChangeDTO) {
        try {
            log.info("Received price change message for product ID: {}", priceChangeDTO.getProductId());

          String storagePath = "/app/price-changes";
          Path storageDir = Paths.get(storagePath);
            if (!Files.exists(storageDir)) {
                Files.createDirectories(storageDir);
                log.info("Created storage directory: {}", storagePath);
            }

            String timestamp = priceChangeDTO.getChangeDate()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("price_change_%d_%s.json", 
                    priceChangeDTO.getProductId(), timestamp);
            
            Path filePath = storageDir.resolve(fileName);

            objectMapper.writeValue(filePath.toFile(), priceChangeDTO);
            log.info("Price change JSON file created: {}", filePath.toString());

            PriceChangeLog priceChangeLog = new PriceChangeLog();
            priceChangeLog.setProductId(priceChangeDTO.getProductId());
            priceChangeLog.setChangeDate(priceChangeDTO.getChangeDate());
            priceChangeLog.setFilePath(filePath.toString());
            priceChangeLog.setFileName(fileName);

            PriceChangeLog savedLog = priceChangeLogRepository.save(priceChangeLog);
            log.info("Price change log saved with ID: {} for product: {}", 
                    savedLog.getId(), priceChangeDTO.getProductId());

        } catch (IOException e) {
            log.error("Error writing price change JSON file for product: {}", 
                    priceChangeDTO.getProductId(), e);
            throw new RuntimeException("Failed to write price change file", e);
        } catch (Exception e) {
            log.error("Error processing price change message for product: {}", 
                    priceChangeDTO.getProductId(), e);
            throw new RuntimeException("Failed to process price change message", e);
        }
    }
}