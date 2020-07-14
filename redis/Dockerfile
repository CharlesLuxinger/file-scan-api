FROM redis:latest

RUN apt-get update -y && \
    apt-get install -y gettext-base --no-install-recommends && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY redis.conf /etc/redis/redis.conf.template
COPY start.sh /start.sh

ENTRYPOINT [ "/bin/bash", "/start.sh" ]