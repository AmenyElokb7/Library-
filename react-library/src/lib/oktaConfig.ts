export const oktaConfig = {
    clientId: '0oae1up6zntUwH4mH5d7',
    issuer: 'https://dev-88895352.okta.com/oauth2/default',
    redirectUri: 'http://127.0.0.1:3000/login/callback',
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck: true,
}