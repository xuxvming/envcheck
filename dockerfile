FROM postgres:11.2-alpine
ENV POSTGRES_USER interview_dbuser
ENV POSTGRES_PASSWORD pass
ENV POSTGRES_DB interview
ADD src/main/resources/. /docker-entrypoint-initdb.d/