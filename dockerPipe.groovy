pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        MYSQL_CREDENTIALS = credentials('mysql-credentials')
        

    }
 stages {
        stage('Checkout Backend') {
            steps {
                git url: 'https://github.com/Hamma-Mrabet/Stage-RFC.git', branch: 'main', credentialsId: 'github-credentials-id'
            }
        }
        stage('Build Backend Docker Image') {
            steps {
                sh 'docker build -t stage-rfc-backend .'
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh 'docker tag stage-rfc-backend hammamrabet/stage-rfc-backend:$BUILD_NUMBER'
                sh 'docker push hammamrabet/stage-rfc-backend:$BUILD_NUMBER'
            }
        }

        stage('Checkout Frontend') {
            steps {
                git url: 'https://github.com/Hamma-Mrabet/Stage-RFC-Front.git', branch: 'main', credentialsId: 'github-credentials-id'
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                sh 'docker build -t stage-rfc-frontend .'
                sh 'docker tag stage-rfc-frontend hammamrabet/stage-rfc-frontend:$BUILD_NUMBER'
                sh 'docker push hammamrabet/stage-rfc-frontend:$BUILD_NUMBER'
            }
        }

        stage('Deploy Application') {
            steps {
                // Create Docker network
                sh 'docker network create my-network || true'

                // Stop and remove old containers
                sh 'docker stop mysql_db || true'
                sh 'docker rm mysql_db || true'
                sh 'docker stop backend_ctr || true'
                sh 'docker rm backend_ctr || true'
                sh 'docker stop frontend_ctr || true'
                sh 'docker rm frontend_ctr || true'

                // Start MySQL container
                sh 'docker run -d --network my-network -p 3306:3306 --name mysql_db -e MYSQL_ROOT_PASSWORD=$MYSQL_CREDENTIALS_PSW mysql:latest'

                // Start Backend container
                sh 'docker run -d --network my-network -p 8089:8089 --name backend_ctr hammamrabet/stage-rfc-backend:$BUILD_NUMBER'

                // Start Frontend container
                sh 'docker run -d --network my-network -p 80:80 --name frontend_ctr hammamrabet/stage-rfc-frontend:$BUILD_NUMBER'

               






            }
        }

    

        
        
       


    }
}



