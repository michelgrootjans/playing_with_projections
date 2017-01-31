This code runs on php 7+.

First run composer for autoloading

```
composer dump-autoload
```
To run the script use the following command

```
php run.php
```
In case you don't have php 7 on your machine, you can build a docker container from the Dockerfile with the following commands

```
docker build -t pwp/php .
```
```
docker run -it --rm -v $(pwd)/../:/home/pwp pwp/php /bin/sh
```