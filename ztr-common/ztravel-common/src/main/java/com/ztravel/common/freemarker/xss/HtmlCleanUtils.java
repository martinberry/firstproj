package com.ztravel.common.freemarker.xss;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlCleanUtils {
   private static final Logger LOGGER = LoggerFactory.getLogger(HtmlCleanUtils.class);

   public static String clean(String html) {
      return clean(html, null);
   }

   public static String clean(String html, Whitelist whitelist) {
      LOGGER.debug("unsafe string:{}", html);
      whitelist = whitelist == null ? Whitelist.basic() : whitelist;
      String safe = Jsoup.clean(html, whitelist);
      LOGGER.debug("safe string:{}", safe);
      return safe;
   }

}
