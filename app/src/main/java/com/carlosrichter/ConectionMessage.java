package com.carlosrichter;

import base.Message;

public class ConectionMessage implements Message {
    public boolean isConextionStatus() {
        return conextionStatus;
    }

    private boolean conextionStatus = false;

    public ConectionMessage(boolean conextionStatus) {
        this.conextionStatus = conextionStatus;
    }


}
