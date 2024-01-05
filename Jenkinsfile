pipeline {
    agent any

    environment {
        // Define environment variables
        DOCKER_IMAGE_BACKEND  = "amenyelokb/library_backend:latest"
        DOCKER_IMAGE_FRONTEND = "amenyelokb/react-library:latest"
        KUBECONFIG_CREDENTIALS_ID = 'jenkins-sa'
        KUBECONFIG = "/var/lib/jenkins/kubeconfig"
        
    }
        // Add other environment variables if necessary
    
    stages {
        stage('Checkout') {
            steps {
                // Get the latest code from your source control
                checkout scm
            }
        }
      /*  stage('Start Services') {
            steps {
                script {
                    sh 'docker-compose up -d'
                }
            }
        }*/
    /*    stage('Print Kubeconfig') {
            steps {
                script {
                    // Read and print the contents of the kubeconfig file
                    def kubeconfigContents = readFile("/var/lib/jenkins/.kube/config")
                    echo "Kubeconfig Contents:\n${kubeconfigContents}"
                }
            }
        }

     
        //
        stage('Remove Existing Containers') {
    steps {
        script {
            // Force remove the backend container if it exists
            sh 'docker rm -f backend || true'
        }
    }
}*/

        stage('Create Network') {
    steps {
        script {
            sh 'docker network ls | grep my_network || docker network create my_network'
        }
    }
}

        stage('Start Backend') {
    steps {
        script {
            sh 'docker build -t my-backend ./library_backend'
            sh """
            docker run -d \
              --network=my_network \
              --name backend \
              -e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/reactlibrarydatabase \
              -e SPRING_DATASOURCE_USERNAME=root \
              -e SPRING_DATASOURCE_PASSWORD= \
              -p 8082:8082 \
              my-backend
            """
        }
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
                        //sh 'docker build -t $DOCKER_IMAGE_FRONTEND .'
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
                        // Apply the Kubernetes manifests for frontend and backend
                        sh 'kubectl apply -f k8s/backend/backend-deployment.yaml'
                        sh 'kubectl apply -f k8s/backend/backend-service.yaml'
                        sh 'kubectl apply -f k8s/frontend/frontend-deployment.yaml'
                        sh 'kubectl apply -f k8s/frontend/frontend-service.yaml'
                }
            }
        }

}
   

    post {
        always {
            echo 'Performing post-build actions for both frontend and backend'
            // Additional actions like cleanup or notifications
            sh 'docker rm -f frontend backend db || true'
            sh 'docker network rm my_network || true'
        }
    }
}


