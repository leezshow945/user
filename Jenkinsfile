import groovy.transform.Field

@Field String dev_pattern = ".*dev"
@Field String future_pattern = ".*future"

def get_branch_environment(String job_name) {
    if (job_name =~ dev_pattern) {
        return "dev"
    }else if(job_name =~ future_pattern){
        return "future"
    } else{
        return "uat"
    }
}

node {
    def branch_environment = get_branch_environment env.JOB_NAME
    def branch_name = env.BRANCH_NAME

    sh "echo branch name is $branch_name"
    sh "echo branch environment is $branch_environment"

    stage('checkout') {
        git branch: branch_name, credentialsId: '13438863-a7a0-401f-94be-96d183a16b3e', url: 'git@150.109.58.86:user/user.git'
    }

    sh "echo branch environment is $branch_environment"
    stage('compile') {
        if (branch_environment == "dev") {
            sh '''
            export JAVA_HOME=/opt/jdk1.8.0_171
            /opt/apache-maven-3.5.3/bin/mvn clean package deploy -U
            '''
        }else if(branch_environment == "future"){
            sh '''
            export JAVA_HOME=/opt/jdk1.8.0_171
            /opt/apache-maven-3.5.3/bin/mvn clean deploy package --settings /opt/apache-maven-3.5.3/conf/qa.xml -Pqa -U
            '''
        }else{
            sh '''
            export JAVA_HOME=/opt/jdk1.8.0_171
            /opt/apache-maven-3.5.3/bin/mvn clean deploy package --settings /alldata/apache-maven-3.5.3/conf/settings.xml -P uat -U
             '''
        }
    }


    stage('build image') {
        sh '''
        export harbor=127.0.0.1
        DOCKER_IMAGE=$harbor/library/$JOB_NAME:$BUILD_ID
        docker build -t $DOCKER_IMAGE $WORKSPACE/.
        '''
    }

    stage('push harbor') {
        sh '''
        export harbor=127.0.0.1
        export harbor_user=admin
        export harbor_pwd=Harbor12345
        docker login -u $harbor_user -p $harbor_pwd $harbor
        DOCKER_IMAGE=$harbor/library/$JOB_NAME:$BUILD_ID
        docker push $DOCKER_IMAGE
        docker logout $harbor
        docker rmi -f $DOCKER_IMAGE
        '''
     }


    if(branch_environment == "dev"){
        stage('auto deploy') {
            sh '''
            docker_remote1="docker --tlsverify --tlscacert=/etc/docker/ca.pem  --tlscert=/etc/docker/cert.pem --tlskey=/etc/docker/key.pem -H 150.109.126.5:2376"
            project_name1=user
            docker_remote2="docker --tlsverify --tlscacert=/etc/docker/ca.pem  --tlscert=/etc/docker/cert.pem --tlskey=/etc/docker/key.pem -H 150.109.126.89:2376"
            project_name2=user2
            send_dingding(){
                /bin/curl 'https://oapi.dingtalk.com/robot/send?access_token=5ffb8b4c70c370840b2b187cfdadb409e60317eddf133df6d5b3ecbf93b92e2f' \
                -H 'Content-Type: application/json' \
                -d '
                {"msgtype": "text",
                    "text": {
                    "content": "'"${1}"'"
                    }
                 }'
            }

			$docker_remote1 pull harbor.xm6f.com/library/$JOB_NAME:$BUILD_ID && \
			$docker_remote1 rm -f $project_name1 && \
			$docker_remote1 run --restart=always --name $project_name1 -d -e SPRING_PROFILES_ACTIVE=dev -e SPRING_CLOUD_ZOOKEEPER_CONNECT-STRING=119.28.142.168:2181 -e MOTAN_IP_REGISTRY=150.109.126.5 -p 30001:30001 -p 46821:8080 -p 8891:8899 harbor.xm6f.com/library/$JOB_NAME:$BUILD_ID && \
			send_dingding 3.0_本地${JOB_NAME}构建流水线版${BUILD_ID}一版

			$docker_remote2 pull harbor.xm6f.com/library/$JOB_NAME:$BUILD_ID && \
			$docker_remote2 rm -f $project_name2 && \
			$docker_remote2 run --restart=always --name $project_name2 -d -e SPRING_PROFILES_ACTIVE=dev -e SPRING_CLOUD_ZOOKEEPER_CONNECT-STRING=119.28.142.168:2181 -e MOTAN_IP_REGISTRY=150.109.126.89 -p 30001:30001  harbor.xm6f.com/library/$JOB_NAME:$BUILD_ID && \
			send_dingding 3.0_本地${JOB_NAME}2构建流水线版${BUILD_ID}一版
            '''
        }
    }else if(branch_environment == "future"){
        stage('auto deploy') {
            sh '''
            docker_remote="docker --tlsverify -H tcp://future5:2376"
            project_name=user
            send_dingding(){
                /bin/curl 'https://oapi.dingtalk.com/robot/send?access_token=5ffb8b4c70c370840b2b187cfdadb409e60317eddf133df6d5b3ecbf93b92e2f' \
                -H 'Content-Type: application/json' \
                -d '
                {"msgtype": "text",
                "text": {
                    "content": "'"${1}"'"
                        }
                }'
            }
            $docker_remote pull harbor.xm6f.com/library/$JOB_NAME:$BUILD_ID  && \
            $docker_remote rm -f $project_name  && \
            $docker_remote run --restart=always --name user -d -p 30001:8080  -e SPRING_PROFILES_ACTIVE=future -e SPRING_CLOUD_CONSUL_HOST=119.28.87.92  -e SPRING_CLOUD_CONSUL_DISCOVERY_IP_ADDRESS=124.156.108.157  harbor.xm6f.com/library/$JOB_NAME:$BUILD_ID && \
            send_dingding ${JOB_NAME}构建流水线版${BUILD_ID}一版
            '''
        }
    }
}