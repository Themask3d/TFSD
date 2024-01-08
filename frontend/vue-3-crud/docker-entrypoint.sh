#!/bin/sh

# Replace the PORT in the Nginx configuration
envsubst '$PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf

# Function to replace backend URL and display the file name if a replacement was made
replace_backend_url() {
    local file=$1
    if sed -i 's|http://localhost:8080|'"$BACKEND"'|g' "$file"; then
        echo "Replaced Backend URLs in $file"
    fi
}

replace_frontend_url() {
    local file=$1
    if sed -i 's|localhost:8081|localhost:'"$PORT"'|g' "$file"; then
        echo "Replaced Frontend URLs in $file"
    fi
}

# Loop through all .js and .map files in the dist/js directory
for file in /usr/share/nginx/html/js/*.js  /usr/share/nginx/html/js/*.map; do
  replace_backend_url "$file"
done

# Loop through all .js and .map files in the dist/js directory
for file in /usr/share/nginx/html/js/*.js /usr/share/nginx/html/js/*.map; do
  replace_frontend_url "$file"
done

echo "Backend running on ${BACKEND}"
echo "Frontend running on http://localhost:${PORT}"

# Start Nginx
exec nginx -g 'daemon off;'