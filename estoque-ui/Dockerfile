FROM node:22-alpine AS build-stage
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
EXPOSE 5173
CMD ["npm", "run", "dev", "--", "--host"]
# RUN npm run dev

# FROM nginx:stable-alpine AS production-stage

# COPY --from=build-stage /app/dist /usr/share/nginx/html

# EXPOSE 5173

# CMD ["nginx", "-g", "daemon off;"]