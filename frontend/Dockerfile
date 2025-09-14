FROM nginx:1.29
LABEL authors="Ariane"

COPY dist /usr/share/nginx/html

COPY config/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]