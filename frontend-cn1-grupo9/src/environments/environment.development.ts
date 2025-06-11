export const environment = {
    production: false,
    useMockData: false,
    apiUrl: 'http://localhost:8080/api',
    bffUrl: 'http://localhost:8080',
    msalConfig: {
        auth: {
            clientId: '59d93efc-d6d0-44a2-8107-35a3315120f9',
        },

    },
    apiConfig: {
        scopes: ['User.Read'],
        uri: 'https://graph.microsoft.com/v1.0/me',
    },
};
