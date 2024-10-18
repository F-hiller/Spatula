package com.ovg.spatula.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class NicknameGenerator {

  private static final List<String> traits = Arrays.asList(
      "활기찬", "장난스러운", "용감한", "신중한", "사려깊은", "친절한", "조용한", "호기심 많은", "느긋한", "열정적인", "현명한", "냉철한",
      "사나운", "우아한", "명랑한", "강력한", "낙천적인", "날렵한", "뚝심 있는", "도전적인", "유쾌한", "거침없는"
  );

  private static final List<String> names = Arrays.asList(
      "아르마딜로", "코끼리", "사자", "토끼", "늑대", "판다", "여우", "호랑이", "고양이", "올빼미", "부엉이", "다람쥐",
      "코알라", "원숭이", "캥거루", "곰", "독수리", "치타", "하마", "악어", "말", "수달"
  );

  public static String generateNickname() {
    Random random = new Random();

    String randomTrait = traits.get(random.nextInt(traits.size()));
    String randomName = names.get(random.nextInt(names.size()));

    return randomTrait + " " + randomName;
  }
}