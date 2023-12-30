pipeline {
    agent any

    environment {
        // Define environment variables
        DOCKER_IMAGE_BACKEND  = "amenyelokb/library_backend:latest"
        DOCKER_IMAGE_FRONTEND = "amenyelokb/react-library:latest"
        KUBECONFIG_CREDENTIALS_ID = 'jenkins-k8s-sa'
        KUBE_CONFIG = "${WORKSPACE}/kubeconfig.yaml"
    }
        // Add other environment variables if necessary
    
    stages {
        stage('Checkout') {
            steps {
                // Get the latest code from your source control
                checkout scm
            }
        }

        stage('Build Backend') {
            steps {
                script {
                    dir('library_backend') {
                        echo 'Build started successfully'
                        // Building the backend Docker image
                        sh 'docker build -t $DOCKER_IMAGE_BACKEND .'
                        echo 'Build completed successfully'

                    }
                }
            }
        }



        stage('Build Frontend') {
            steps {
                script {
                    dir('react-library') {
                        // Building the frontend Docker image
                        sh 'docker build -t $DOCKER_IMAGE_FRONTEND .'
                    }
                }
            }
        }
       stage('Test Backend') {
            steps {
                script {
                    dir('library_backend') {
                        // Running backend tests
                      //  sh 'docker run --rm $DOCKER_IMAGE_BACKEND java -jar app.jar test'
                    }
                }
            }
        }

        stage('Test Frontend') {
            steps {
                script {
                    dir('react-library') {
                        // Running frontend tests
                       // sh 'docker run --rm $DOCKER_IMAGE_FRONTEND npm test'
                    }
                }
            }
        }

        stage('Push to Docker Registry') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials') {
                        docker.image("$DOCKER_IMAGE_BACKEND").push()
                        docker.image("$DOCKER_IMAGE_FRONTEND").push()
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                script {
                // Use the actual KUBE_TOKEN from Jenkins credentials
                withCredentials([string(credentialsId: 'jenkins-sa', variable: 'tDYu2i9E/WY4mE7lN5Dh6g==')]) {
                    // Apply the Kubernetes manifests for frontend and backend to Minikube
                    sh 'export KUBECONFIG=/home/devops/.kube/config'

                    
                    // Apply the Kubernetes manifests for frontend and backend
                    sh "kubectl apply -f k8s/backend/backend-deployment.yaml"
                    sh "/usr/local/bin/kubectl apply -f k8s/frontend/frontend-deployment.yaml"
                    
                    // Apply the Kubernetes service manifest
                    sh "/usr/local/bin/kubectl apply -f k8s/frontend/frontend-service.yaml"
            }
        }
    }
}

         

    }
   

    post {
        always {
            echo 'Performing post-build actions for both frontend and backend'
            // Additional actions like cleanup or notifications
        }
    }
}


