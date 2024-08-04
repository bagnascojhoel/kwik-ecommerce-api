FROM ubuntu
ARG ARTIFACT
COPY build/native/nativeCompile/${ARTIFACT} /app
EXPOSE 8080
ENTRYPOINT ["/app"]