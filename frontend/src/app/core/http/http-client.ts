import axios from 'axios';

const httpClient = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000,
});

export default httpClient;
