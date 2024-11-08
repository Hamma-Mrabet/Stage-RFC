pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        MYSQL_CREDENTIALS = credentials('mysql-credentials')
    }

    stages {
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
        
       
        
          stage('Terraform init') {
            steps {
                sh 'terraform init --upgrade'
                sh 'terraform plan'
            }
        }
        
        stage('Terraform apply') {
            steps {
                sh 'terraform apply --auto-approve'
                
            }
        }
       
        
         stage('Get Kubernetes Config') {
            steps {
                script {
                    // Extract the kubeconfig output from Terraform and save it to a file
                    def kubeconfig = sh(script: "terraform output -raw kube_config", returnStdout: true).trim()
                    writeFile file: 'kubeconfig', text: kubeconfig
                    sh 'chmod 600 kubeconfig' // Secure the kubeconfig file
                }
            }
        }
        
        stage('K8S Deploy') {
            steps {
                script {
                    // Use the kubeconfig file with the kubectl command
                    sh 'kubectl apply -f deployment.yaml --kubeconfig=kubeconfig'
                }
            }
        }
        
        
    }
}
