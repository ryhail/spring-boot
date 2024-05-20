FROM node:alpine
WORKDIR /usr/app/front
COPY package.json /usr/app/front/package.json
EXPOSE 3000
COPY ./ ./
RUN npm install
CMD ["npm", "start"]
