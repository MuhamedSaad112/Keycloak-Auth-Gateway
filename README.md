# 🔐 Keycloak Auth Gateway

## 🌟 Overview
A modern authentication solution using Keycloak with Spring Boot, providing robust security and user management capabilities.

## ✨ Core Features

### 🛡️ Authentication & Security
- Keycloak Integration (v26.1.0)
- OAuth2/OpenID Connect Support
- Role-Based Access Control (RBAC)
- Token Management System
- Multi-Factor Authentication

### 🚀 Technical Stack
- Spring Boot Application
- Java 17
- Docker & Docker Compose
- Bridge Network Architecture
- Volume-Based Storage

## 🔧 Prerequisites
- Docker & Docker Compose
- Java 17+
- Available ports: 8080, 5555
- Git

## 📦 Quick Start

### 1. Clone Repository
```bash
git clone https://github.com/MuhamedSaad112/Keycloak-Auth-Gateway.git
cd Keycloak-Auth-Gateway
```

### 2. Start Services
```bash
docker-compose up -d
```

### 3. Access Points
- Keycloak Admin: `http://localhost:8080`
- Application: `http://localhost:5555`

## ⚙️ Configuration

### Keycloak Setup
- **Admin Credentials**
  - Username: admin
  - Password: admin
- **Realm**: basic-vote-realm
- **Client ID**: basic-vote

### Application Configuration
- Spring Profile: Production
- Port: 5555
- Logging: `/app/logs`

## 🔍 System Architecture

### Components
- **Keycloak Server**
  - Authentication provider
  - User management
  - Role management
  
- **Spring Boot Application**
  - Business logic
  - API endpoints
  - Security integration

### Network Setup
- Internal network: app_network
- Bridge driver
- Health checks enabled

## 🛠️ Development

### Local Setup
1. Clone repository
2. Configure environment
3. Start services
4. Run tests

### Environment Variables
- SPRING_PROFILES_ACTIVE
- KEYCLOAK_URL
- CLIENT_ID
- CLIENT_SECRET

## 📝 Logging & Monitoring

### Health Checks
- 30-second intervals
- 10-second timeout
- 5 retry attempts

### Logging
- Application logs
- Keycloak audit logs
- Container logs

## 🚨 Troubleshooting

### Common Issues
1. **Port Conflicts**
   - Check port availability
   - Verify service status

2. **Connection Issues**
   - Check network configuration
   - Verify service health

3. **Authentication Problems**
   - Verify Keycloak configuration
   - Check client settings

## 🔒 Security Best Practices
- Change default credentials
- Enable HTTPS in production
- Regular security updates
- Monitor access logs
- Implement rate limiting

## 🤝 Contributing
Feel free to:
- Submit issues
- Propose features
- Create pull requests
- Improve documentation

## ✨ Author
- [Mohamed Saad](https://github.com/MuhamedSaad112)
