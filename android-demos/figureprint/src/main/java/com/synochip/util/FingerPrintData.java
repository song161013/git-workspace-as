package com.synochip.util;

import java.util.List;

/**
 * Created by songfei on 2018/7/2
 * Description：
 */
public class FingerPrintData {

    private String error;
    private int code;
    private String message;
    private List<Result> result;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResults() {
        return result;
    }

    public void setResults(List<Result> result) {
        this.result = result;
    }

    public static class Result {
        List<Fingerprintlist> fingerprintlist;
        private String password;
        private String fingerprint;
        private String user_name;
        private String d_id;

        public String getFingerprint() {
            return fingerprint;
        }

        public void setFingerprint(String fingerprint) {
            this.fingerprint = fingerprint;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getD_id() {
            return d_id;
        }

        public void setD_id(String d_id) {
            this.d_id = d_id;
        }

        public List<Fingerprintlist> getFingerprintlist() {
            return fingerprintlist;
        }

        public void setFingerprintlist(List<Fingerprintlist> fingerprintlist) {
            this.fingerprintlist = fingerprintlist;
        }

        public static class Fingerprintlist {
            private String password;
            private String code;
            private String user_name;
            private String d_id;

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getD_id() {
                return d_id;
            }

            public void setD_id(String d_id) {
                this.d_id = d_id;
            }
        }

    }
}
