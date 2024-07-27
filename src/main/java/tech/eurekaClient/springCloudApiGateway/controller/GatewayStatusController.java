package tech.eurekaClient.springCloudApiGateway.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/")
public class GatewayStatusController {

  @GetMapping("")
  public ResponseEntity<Map<String,String>> getStatus(){
    log.info ("Up and Running");
    return ResponseEntity.ok (Map.of ("Status", HttpStatus.OK.name ()));
  }
}
