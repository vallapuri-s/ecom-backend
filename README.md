# Ecom Backend - Hands on repository

### Cloning the repository

```bash
git clone https://github.com/thoughtworks-hands-on/ecom-backend.git
```
### Install java 17 using sdkman
* Download sdkman with the following curl command
```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk list java
sdk install java 17.0.7-amzn
sdk use java 17.0.7-amzn
java -version
echo $JAVA_HOME
```

### Setting up java runtime on intellij
* Open the project in Intellij
* Go to File -> Project Structure -> Project Settings -> Project -> Project SDK -> Select the java 17 sdk
* Set up the module sdk in project settings to 17 as well
* Set up the gradle jvm version to 17 in Settings -> Build, Execution, Deployment -> Build Tools -> Gradle -> Gradle JVM to 17
* Additionally, you might have to set up classpath using Run -> Edit Configurations -> Add new configuration -> Application -> Main class -> EcomBackendApplication -> Use classpath of module -> ecom-backend_main
* Restart IntelliJ

### Running the application
* Run the main method in EcomBackendApplication.java or using the command
```
./gradlew bootRun
```

### Running the tests
* Run the tests using the gradle command
```
./gradlew test
```

### Viewing the H2 database from the console
#### Note: H2 database is an in memory database that will be reset every time the application is restarted
* After starting the application, we can navigate to `http://localhost:8080/h2-console`, which will present us with a login page.
* Console login screen will look like this

![img.png](src/main/resources/static/h2_connection.png)
* Type in the password as `password` and click on connect
* We can now view the tables and run queries on the database

### Using the Collection to test the APIs
* If you are familiar with Insomnia, collection can be found as [ecom-Insomnia_collection.json](src/main/resources/collections/ecom-Insomnia_collection.json)
* If you are comfortable with postman, collection can be found as [ecom-Postman_collection.json](src/main/resources/collections/ecom.postman_collection.json)

## Docker and CI/CD

### Running with Docker

#### Build the Docker image locally:
```bash
docker build -t ecom-backend .
```

#### Run the container:
```bash
docker run -p 8080:8080 ecom-backend
```

#### Access the application:
- Main application: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console

### GitHub Actions CI/CD Pipeline

The repository includes GitHub Actions workflows for automated testing and Docker image building with AWS ECR integration:

#### Workflows:
1. **Test Application** (`test` job):
   - Runs on every push and pull request
   - Sets up Java 17 environment
   - Caches Gradle dependencies for faster builds
   - Runs all tests with `./gradlew test`
   - Uploads test results as artifacts

2. **Build Docker Image** (`build-docker` job):
   - Runs only on pushes to `main` or `develop` branches
   - Builds and pushes Docker image to AWS ECR
   - Uses Docker layer caching for faster builds
   - Tags images with commit SHA and `latest` tag

3. **Security Scan** (`security-scan` job):
   - Runs vulnerability scanning on the built image
   - Uses Trivy for container security analysis
   - Uploads results to GitHub Security tab

#### How it works:
1. When you push code to `main` or `develop` branches, the workflow automatically:
   - Runs all tests to ensure code quality
   - Authenticates with AWS using OIDC
   - Builds a Docker image if tests pass
   - Pushes the image to AWS ECR
   - Performs security scanning

#### Accessing the built image:
```bash
# Configure AWS CLI
aws configure

# Pull the latest image
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin {AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com
docker pull {AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/ecom-backend:latest

# Run the container
docker run -p 8080:8080 {AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/ecom-backend:latest
```

#### Prerequisites:
- AWS ECR repository named `ecom-backend` in `us-east-1` region
- AWS IAM role with ECR permissions configured for GitHub Actions OIDC
- GitHub secret `AWS_ROLE_ARN` containing the IAM role ARN
- ECR repository policy allowing push/pull access

#### AWS Setup Required:
1. **Create ECR Repository**:
   ```bash
   aws ecr create-repository --repository-name ecom-backend --region us-east-1
   ```

2. **Configure IAM Role for GitHub Actions**:
   - Create IAM role with ECR permissions
   - Configure OIDC provider for GitHub Actions
   - Add the role ARN to GitHub repository secrets as `AWS_ROLE_ARN`

3. **ECR Repository Policy** (optional):
   ```json
   {
     "Version": "2012-10-17",
     "Statement": [
       {
         "Sid": "AllowGitHubActions",
         "Effect": "Allow",
         "Principal": {
           "AWS": "arn:aws:iam::YOUR_ACCOUNT_ID:role/YOUR_GITHUB_ACTIONS_ROLE"
         },
         "Action": [
           "ecr:GetDownloadUrlForLayer",
           "ecr:BatchGetImage",
           "ecr:BatchCheckLayerAvailability",
           "ecr:PutImage",
           "ecr:InitiateLayerUpload",
           "ecr:UploadLayerPart",
           "ecr:CompleteLayerUpload"
         ]
       }
     ]
   }
   ```
