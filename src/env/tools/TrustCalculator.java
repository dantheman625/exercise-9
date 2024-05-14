package tools;

import cartago.*;

import java.util.*;

public class TrustCalculator extends Artifact {
    
  @OPERATION
  public void agentWithHighestIT(Object[] interactionTrustList,
      OpFeedbackParam<String> agent) {
    HashMap<String, List<Double>> map = new HashMap<String, List<Double>>();
    for (Object interactionTrust : interactionTrustList) {
      Object[] obj = (Object[]) interactionTrust;
      String id = (String)obj[1];
      double rating = ((Number) obj[3]).doubleValue();
      if (map.containsKey(id)) {
        List<Double> newList = map.get(id);
        newList.add(rating);
        map.put(id, newList);
      } else {
        List<Double> list = new ArrayList<Double>();
        list.add(rating);
        map.put(id, list);
      }
    }
    String keyWithHighestValue = "";
    Double highestValue = null;

    for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
      String key = entry.getKey();
      Double value = 0.0;
      for (Double rating : entry.getValue()) {
        value += rating;
      }
      value = value / entry.getValue().size();
      if (highestValue == null ||value > highestValue) {
          keyWithHighestValue = key;
          highestValue = value;
      }
    }

    System.out.println("Agent with the highest average interaction trust: " + keyWithHighestValue + " with average rating: " + highestValue);
    agent.set(keyWithHighestValue);
  }

  @OPERATION
  public void agentWithHighestIT_CR(Object[] interactionTrustList, Object[] certifiedReputationList, 
      OpFeedbackParam<String> agent) {
        HashMap<String, List<Double>> itmap = new HashMap<String, List<Double>>();
        for (Object interactionTrust : interactionTrustList) {
          Object[] obj = (Object[]) interactionTrust;
          String id = (String)obj[1];
          double rating = ((Number) obj[3]).doubleValue();
          if (itmap.containsKey(id)) {
            List<Double> newList = itmap.get(id);
            newList.add(rating);
            itmap.put(id, newList);
          } else {
            List<Double> list = new ArrayList<Double>();
            list.add(rating);
            itmap.put(id, list);
          }
        }
        HashMap<String, Double> crmap = new HashMap<String, Double>();
        for (Object certifiedReputation : certifiedReputationList) {
          Object[] obj = (Object[]) certifiedReputation;
          String id = (String)obj[1];
          double rating = ((Number) obj[3]).doubleValue();
          if (itmap.containsKey(id)) {
            List<Double> itRatings = itmap.get(id);
            Double avg = 0.0;
            for (Double itRating : itRatings) {
              avg += itRating;
            }
            avg = avg / itRatings.size();
            Double combined = avg * 0.5 + rating * 0.5;
            crmap.put(id, combined);
          }
        }
        String keyWithHighestValue = "";
        Double highestValue = null;
    
        for (Map.Entry<String, Double> entry : crmap.entrySet()) {
          String key = entry.getKey();
          Double value = entry.getValue();
          
          if (highestValue == null ||value > highestValue) {
              keyWithHighestValue = key;
              highestValue = value;
          }
        }
        System.out.println("Agent with the highest average interaction trust and certified reputation: " + keyWithHighestValue + " with average rating: " + highestValue);
      }

      @OPERATION
      public void agentWithHighestIT_CR_WR(Object[] interactionTrustList, Object[] certifiedReputationList, 
          Object[] witnessReputationList, OpFeedbackParam<String> agent) {
            HashMap<String, List<Double>> itmap = new HashMap<String, List<Double>>();
            for (Object interactionTrust : interactionTrustList) {
              Object[] obj = (Object[]) interactionTrust;
              String id = (String)obj[1];
              double rating = ((Number) obj[3]).doubleValue();
              if (itmap.containsKey(id)) {
                List<Double> newList = itmap.get(id);
                newList.add(rating);
                itmap.put(id, newList);
              } else {
                List<Double> list = new ArrayList<Double>();
                list.add(rating);
                itmap.put(id, list);
              }
            }
            HashMap<String, Double> crmap = new HashMap<String, Double>();
            for (Object certifiedReputation : certifiedReputationList) {
              Object[] obj = (Object[]) certifiedReputation;
              String id = (String)obj[1];
              double rating = ((Number) obj[3]).doubleValue();
              crmap.put(id, rating);
            }

            HashMap<String, List<Double>> wrmap = new HashMap<String, List<Double>>();
            for (Object interactionTrust : interactionTrustList) {
              Object[] obj = (Object[]) interactionTrust;
              String id = (String)obj[1];
              double rating = ((Number) obj[3]).doubleValue();
              if (wrmap.containsKey(id)) {
                List<Double> newList = wrmap.get(id);
                newList.add(rating);
                wrmap.put(id, newList);
              } else {
                List<Double> list = new ArrayList<Double>();
                list.add(rating);
                wrmap.put(id, list);
              }
            }
            String keyWithHighestValue = "";
            Double highestValue = null;
    
            for (Map.Entry<String, List<Double>> entry : itmap.entrySet()) {
              String key = entry.getKey();
              Double itValue = 0.0;
              for (Double rating : entry.getValue()) {
                itValue += rating;
              }
              itValue = itValue / entry.getValue().size();
              Double wrValue = 0.0;
              for (Double rating : wrmap.get(key)) {
                wrValue += rating;
              }
              wrValue = wrValue / wrmap.get(key).size();
              Double value = itValue / 3 + wrValue / 3 + crmap.get(key) / 3;
              if (highestValue == null || value > highestValue) {
                  keyWithHighestValue = key;
                  highestValue = value;
              }
            }
    
            System.out.println("Agent with the highest average interaction trust, certified reputation, and witness reputation: " + keyWithHighestValue + " with average rating: " + highestValue);
          }
}
