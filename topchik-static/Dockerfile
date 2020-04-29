FROM node:13.12.0-alpine as build-stage
WORKDIR /usr/src/topchik/topchik-static
COPY package.json yarn.lock /usr/src/topchik/topchik-static/
RUN yarn install
COPY . /usr/src/topchik/topchik-static/
RUN yarn build


FROM nginx:stable-alpine
COPY --from=build-stage /usr/src/topchik/topchik-static/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 8081
CMD ["nginx", "-g", "daemon off;"]
