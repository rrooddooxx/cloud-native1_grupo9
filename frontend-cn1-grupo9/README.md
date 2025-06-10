# E-Commerce Cloud

Una aplicación de comercio electrónico desarrollada con Angular 19 y cloud-native architecture.

## 🚀 Tecnologías

- **Angular**: 19.2.0
- **TypeScript**: 5.7.2
- **Bootstrap**: 5.3.3 con ng-bootstrap
- **SCSS**: Para estilos personalizados
- **RxJS**: Para programación reactiva

## 📋 Prerequisitos

- Node.js (versión LTS recomendada)
- npm o yarn
- Angular CLI

## 🛠️ Instalación

```bash
# Clonar el repositorio
git clone <repository-url>
cd ecomm-cloud

# Instalar dependencias
npm install
```

## 🏃‍♂️ Comandos Disponibles

```bash
# Servidor de desarrollo
npm start
# o
ng serve

# Build para producción
npm run build

# Build con watch para desarrollo
npm run watch

# Ejecutar tests
npm test
```

## 📁 Estructura del Proyecto

```
src/
└── app/
    ├── core/                  # Contiene elementos globales y reutilizables
    │   ├── auth/              # Funciones relacionadas a autenticación
    │   ├── components/        # Componentes compartidos en toda la app
    │   ├── services/          # Servicios globales
    │   ├── shell/             # Componentes de layout (header, sidebar, etc.)
    │   ├── mattersList.ts     # Listas maestras (usadas en filtros, selects)
    │   └── searchFields.ts    # Campos comunes de búsqueda
    │
    └── features/              # Cada dominio funcional o módulo va aquí
        └── <feature-name>/    # Ej: products, orders, users
            ├── models/        # Interfaces y estructuras de datos
            ├── services/      # Servicios específicos del feature
            ├── components/    # Componentes internos del módulo
            ├── pages/         # Páginas/vistas divididas por función
            │   └── <page-name>/
            │       ├── <page-name>.component.ts
            │       ├── <page-name>.component.html
            │       ├── <page-name>.component.scss
            │       └── <page-name>.component.spec.ts
            ├── routes/        # Configuración de rutas internas
            └── <feature-name>.routes.ts
```

## 🧩 Convenciones de Desarrollo

### Estructura Modular
- Cada feature debe tener su propia carpeta dentro de `features/`
- Cada página se maneja como una carpeta separada con su componente completo
- Los modelos de datos van en `models/`
- Servicios locales en `services/`, servicios globales en `core/services`

### Nomenclatura
- Componentes: PascalCase (ej: `ProductListComponent`)
- Archivos: kebab-case (ej: `product-list.component.ts`)
- Servicios: PascalCase terminando en Service (ej: `ProductService`)

## 🏗️ Creando Nuevos Módulos

1. Crear carpeta en `features/<nuevo-feature>/`
2. Crear subcarpetas: `models/`, `pages/`, `services/`, etc.
3. Crear archivo de rutas: `<nuevo-feature>.routes.ts`
4. Definir páginas bajo `pages/` como carpetas con su componente completo

## 🌐 Configuración de Desarrollo

El proyecto está configurado con:
- Servidor de desarrollo en modo watch
- Source maps habilitados
- Hot reload automático
- Budgets de tamaño para optimización

## 📦 Build y Deploy

```bash
# Build optimizado para producción
npm run build

# Los archivos se generan en dist/ecomm-cloud/
```

## 🧪 Testing

El proyecto utiliza Jasmine y Karma para testing:

```bash
npm test
```

## 📝 Notas

- El proyecto usa SCSS como preprocesador de CSS
- Bootstrap está integrado con ng-bootstrap para componentes Angular nativos
- Configurado para internacionalización con Angular i18n
