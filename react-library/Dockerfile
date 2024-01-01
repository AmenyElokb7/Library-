# Stage 1: Build the React application
FROM node:16

# Set the working directory in the Docker image
WORKDIR urs/src/app

# Copy package.json and package-lock.json (or yarn.lock if using Yarn)
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the source code into the image
COPY . .

# Build the application
RUN npm run build

# Stage 2: Serve the app with Nginx
#FROM nginx:stable-alpine

# Copy the build artifacts from the build stage
#COPY --from=build /app/build /usr/share/nginx/html

# Copy the Nginx configuration
#COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 5173

# Start Nginx when the container has provisioned
#CMD ["nginx", "-g", "daemon off;"]
CMD ["npm","run","dev"]:
