export const environment = {
  production: false,
  useMockData: false,
  apiUrl: 'http://localhost:8080/api',
  // bffUrl: 'https://kegbnhdml7.execute-api.us-east-1.amazonaws.com/production',
  // bffUrl: 'http://localhost:8080/api',
  bffUrl: 'https://v9l4qmcvla.execute-api.us-east-1.amazonaws.com/prod/bff/api',
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
