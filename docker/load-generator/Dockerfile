FROM chainguard/wolfi-base:latest

ENV ENDPOINT=http://app:8080/products

RUN set -eux; \
    apk add --no-cache \
        hey \
    ; \
    rm -rf /var/cache/apk/*;

ENTRYPOINT ["sh", "-c", "sleep 30 && while true; do hey -n 100 -c 10 $ENDPOINT > /dev/null 2>&1; done"]
