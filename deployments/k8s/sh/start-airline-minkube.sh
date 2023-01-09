#!/bin/sh
minikube start
# останавливаем существующие поды приложения, чтобы впоследствии закешировать актуальный образ
kubectl --namespace default scale deployment airline-project-deployment --replicas 0
# приостанавливаем выполнение скрипта на 10 секунд, чтобы контейнер успел остановиться
sleep 10
# удаляем закешированный в миникубе образ приложения
# если на этом шаге в логах видите ошибку, попробуйте увеличить время на предыдущем шаге
minikube image rm airline-project
# загружаем актуальный образ приложения в кеш миникуба из локального репозитория
minikube image load airline-project
# запускаем поду приложения с новым образом
kubectl --namespace default scale deployment airline-project-deployment --replicas 1
minikube dashboard