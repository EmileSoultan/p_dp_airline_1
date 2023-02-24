minikube start
rem останавливаем существующие поды приложения, чтобы впоследствии закешировать актуальный образ
kubectl --namespace default scale deployment airline-project-deployment --replicas 0
rem приостанавливаем выполнение скрипта на 10 секунд, чтобы контейнер успел остановиться
timeout /t 10
rem удаляем закешированный в миникубе образ приложения
rem если на этом шаге в логах видите ошибку, попробуйте увеличить время на предыдущем шаге
minikube image rm airline-project
rem загружаем актуальный образ приложения в кеш миникуба из локального репозитория
minikube image load airline-project
rem запускаем поду приложения с новым образом
kubectl --namespace default scale deployment airline-project-deployment --replicas 1
minikube dashboard