pipeline {
    // 환경설정 (도커허브와 jenkins)
    environment {
        repository = "gentledog/gentledog" //dockerHub id와 repository 이름
        DOCKERHUB_CREDENTIALS = credentials('jenkins') // jenkins에 등록해놓은 docker hub credentials 이름
        dockerImage = ''
    }

    agent any
    stages {
        // 현재 스프링 서버를 이미지로 빌드시킴
        stage('Build Image') {
            steps {
                // 빌드에 실패하면 중지
                sh '''
                    echo 이미지 빌드 시작
                    chmod +x gradlew || true
                    ./gradlew build
                    dockerImage = docker.build repository + ":${BUILD_NUMBER}"
                    echo 이미지 빌드 종료
                '''
            }
        }
        // 도커 허브에 로그인
        stage('DockerHub Login'){
            steps {
                sh '''
                    echo 도커 허브 로그인 시작
                    echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin
                    echo 도커 허브 로그인 종료
                '''
            }
        }
        // 도커 허브에 이미지 푸쉬
        stage('DockerHub Push'){
            steps {
                script {
                    // BUILD_NUMBER = 현재 빌드의 일련번호로, 젠킨스에서 제공됨
                    sh '''
                        echo 도커 허브에 이미지 푸쉬 시작
                        docker push $repository:${BUILD_NUMBER}
                        echo 도커 허브에 이미지 푸쉬 종료
                    '''
                }
            }
        }


//        stage('DockerSize') {
//            steps {
//                sh '''
//                    docker stop orders || true
//                    docker rm orders || true
//                    docker rmi orders || true
//                    docker build -t orders .
//                    echo "orders: build success"
//                '''
//            }
//        }
//        stage('Deploy') {
//            steps {
//                sh '''
//                docker run -e EUREKA_URL="${EUREKA_URL}" -e MASTER_DB_URL="${MASTER_DB_URL}/orders" -e MASTER_DB_USERNAME="${MASTER_DB_USERNAME}" -e MASTER_DB_PASSWORD="${MASTER_DB_PASSWORD}" -d --name orders --network gentledog orders
//                echo "orders: run success"
//                '''
//                }
//        }
    }
}