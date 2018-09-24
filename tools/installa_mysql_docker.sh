sudo docker pull mysql

sudo docker run -d -p 33061:3306 --name mysql57 -e MYSQL_ROOT_PASSWORD=root mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

sudo docker exec -it mysql57 mysql -uroot -p

# Host: 127.0.0.1
# User: root
# Password: secret
# Puerto: 33061
