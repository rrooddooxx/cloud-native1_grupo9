export const environment = {
    production: true,
    useMockData: false,
    apiUrl: 'https://api.ecomm-cloud.com',
    msalConfig: {
        auth: {
            clientId: '59d93efc-d6d0-44a2-8107-35a3315120f9',
        },

    },
    apiConfig: {
        scopes: ['openid'],
        uri: 'https://graph.microsoft.com/v1.0/me',
    },
};
