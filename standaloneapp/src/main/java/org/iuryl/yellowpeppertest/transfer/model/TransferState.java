package org.iuryl.yellowpeppertest.transfer.model;

public enum TransferState {
    PENDING, //initial state
    PROCESSED, //final state - everything went ok
    ERROR; //final state - some error occurred
}
