echo "Applying ConfigMaps..."
kubectl apply -f infrastructure/base/configmap/

echo "Deploying Redis..."
kubectl apply -f infrastructure/base/redis/inventory-cache/
kubectl apply -f infrastructure/base/redis/product-cache/

echo "Deploying PostgreSQL databases..."
kubectl apply -f infrastructure/base/postgresql/order-db/
kubectl apply -f infrastructure/base/postgresql/payment-db/
kubectl apply -f infrastructure/base/postgresql/recommendation-db/
kubectl apply -f infrastructure/base/postgresql/user-db/

echo "Deploying MongoDB..."
kubectl apply -f infrastructure/base/mongodb/product-db/

echo "Deploying Kafka & Zookeeper..."
kubectl apply -f infrastructure/base/zookeeper/
kubectl apply -f infrastructure/base/kafka/

echo "Deploying Microservices..."
kubectl apply -f services/inventory-service/k8s/
kubectl apply -f services/notification-service/k8s/
kubectl apply -f services/order-service/k8s/
kubectl apply -f services/payment-service/k8s/
kubectl apply -f services/product-service/k8s/
kubectl apply -f services/recommendation-service/k8s/
kubectl apply -f services/user-service/k8s/

echo "Deploying Grafana & Prometheus"
kubectl apply -f infrastructure/monitoring/prometheus/
kubectl apply -f infrastructure/monitoring/grafana/

echo "Applying Ingress..."
kubectl apply -f infrastructure/ingress/ingress.yaml

echo "Deployment complete!"