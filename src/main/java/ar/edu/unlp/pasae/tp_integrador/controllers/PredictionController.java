package ar.edu.unlp.pasae.tp_integrador.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypePredictionRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypePredictionResultDTO;
import ar.edu.unlp.pasae.tp_integrador.services.PhenotypePredictionService;

@RestController
@RequestMapping("/prediction")
public class PredictionController {
  @Autowired
  private PhenotypePredictionService predictionService;

  @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
  public PhenotypePredictionResultDTO create(@RequestBody @Valid PhenotypePredictionRequestDTO predictionRequest) {
    return this.getPredictionService().predict(predictionRequest);
  }

  /**
   * @return the predictionService
   */
  public PhenotypePredictionService getPredictionService() {
    return predictionService;
  }
}