export const environment = {
    production: false,
    useMockData: true,
    apiUrl: 'http://localhost:3000/api',
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
