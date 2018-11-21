package com.jemmy.services.barcode.model.enums;

public enum ShapeEnum {
    FORCENONE{
        @Override
        public String toString() {
            return "force-none";
        }
    },
    FORCESQUARE{
        @Override
        public String toString() {
            return "force-square";
        }
    },
    FORCERECTANGLE{
        @Override
        public String toString() {
            return "force-rectangle";
        }
    }

}
