package tech.eurekaClient.springCloudApiGateway.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Random;

@Component
public class CustomLoadBalancerClient implements LoadBalancerClient {

  private final DiscoveryClient discoveryClient;
  private final Random random;

  public CustomLoadBalancerClient(DiscoveryClient discoveryClient) {
    this.discoveryClient = discoveryClient;
    this.random = new Random();
  }

  @Override
  public ServiceInstance choose(String serviceId) {
    List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
    if (instances.isEmpty()) {
      return null;
    }
    // Custom load balancing logic (e.g., random selection)
    return instances.get(random.nextInt(instances.size()));
  }

  @Override
  public <T> ServiceInstance choose (String serviceId, Request<T> request) {
    return null;
  }

  @Override
  public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
    ServiceInstance instance = choose(serviceId);
    if (instance == null) {
      throw new IllegalStateException("No instances available for " + serviceId);
    }
    try {
      return request.apply(instance);
    } catch (Exception e) {
      throw new RuntimeException (e);
    }
  }

  @Override
  public <T> T execute (String serviceId, ServiceInstance serviceInstance,
      LoadBalancerRequest<T> request) throws IOException {
    return null;
  }

  @Override
  public URI reconstructURI(ServiceInstance instance, URI original) {
    return LoadBalancerUriTools.reconstructURI(instance, original);
  }
}

