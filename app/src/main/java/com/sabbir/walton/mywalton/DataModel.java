package com.sabbir.walton.mywalton;



    public class DataModel {
        private String name;
        private String location;  // Changed from address to match Firebase field
        private String contact;

        // Empty constructor required for Firebase
        public DataModel() {
        }

        public DataModel(String name, String location, String contact) {
            this.name = name;
            this.location = location;
            this.contact = contact;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String getLocation() {  // Changed from getAddress() to match Firebase field
            return location;
        }

        public String getContact() {
            return contact;
        }

        // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setLocation(String location) {  // Changed from setAddress() to match Firebase field
            this.location = location;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }
    }


