package com.yogesh.eventhub.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yogesh on 8/2/16.
 */
public class ArrDate implements Serializable {
        private String EventCode;
        private String ShowDateCode;
        private String ShowDateDisplay;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The EventCode
         */
        public String getEventCode() {
            return EventCode;
        }

        /**
         *
         * @param EventCode
         * The EventCode
         */
        public void setEventCode(String EventCode) {
            this.EventCode = EventCode;
        }

        /**
         *
         * @return
         * The ShowDateCode
         */
        public String getShowDateCode() {
            return ShowDateCode;
        }

        /**
         *
         * @param ShowDateCode
         * The ShowDateCode
         */
        public void setShowDateCode(String ShowDateCode) {
            this.ShowDateCode = ShowDateCode;
        }

        /**
         *
         * @return
         * The ShowDateDisplay
         */
        public String getShowDateDisplay() {
            return ShowDateDisplay;
        }

        /**
         *
         * @param ShowDateDisplay
         * The ShowDateDisplay
         */
        public void setShowDateDisplay(String ShowDateDisplay) {
            this.ShowDateDisplay = ShowDateDisplay;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }


}
