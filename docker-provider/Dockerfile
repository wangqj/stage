FROM java:8
ADD target/docker-provider-2.0-assembly/docker-provider-2.0 /docker-provider-2.0
ENTRYPOINT ["sh","/docker-provider-2.0/bin/start.sh"]
