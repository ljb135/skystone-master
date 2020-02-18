package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name= "MasterClassTesting", group="Testing")
//comment out this line before using
public class MasterClassTesting extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FRDrive = null;
    private DcMotor FLDrive = null;
    private DcMotor BRDrive = null;
    private DcMotor BLDrive = null;
    private DcMotor lift = null;
    private Servo Erectus = null;
    private Servo frontGrab = null;
    private Servo rightGrab = null;
    private Servo leftGrab = null;
    private Servo foundation = null;
    private Servo capstone = null;

    @Override
    public void runOpMode() throws InterruptedException {
        FRDrive = hardwareMap.get(DcMotor.class, "front_right");
        FLDrive = hardwareMap.get(DcMotor.class, "front_left");
        BRDrive = hardwareMap.get(DcMotor.class, "back_right");
        BLDrive = hardwareMap.get(DcMotor.class, "back_left");
        lift = hardwareMap.get(DcMotor.class, "lift");
//        Erectus = hardwareMap.get(Servo.class, "erectus");
//        frontGrab = hardwareMap.get(Servo.class, "front_grab");
//        rightGrab = hardwareMap.get(Servo.class, "right_grab");
//        leftGrab = hardwareMap.get(Servo.class, "left_grab");
//        foundation = hardwareMap.get(Servo.class, "foundation");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        FRDrive.setDirection(DcMotor.Direction.REVERSE);
        FLDrive.setDirection(DcMotor.Direction.FORWARD);
        BRDrive.setDirection(DcMotor.Direction.REVERSE);
        BLDrive.setDirection(DcMotor.Direction.FORWARD);
        lift.setDirection(DcMotor.Direction.FORWARD);
//        Erectus.setDirection(Servo.Direction.FORWARD);
//        frontGrab.setDirection(Servo.Direction.FORWARD);
//        rightGrab.setDirection(Servo.Direction.FORWARD);
//        leftGrab.setDirection(Servo.Direction.REVERSE);
//        foundation.setDirection(Servo.Direction.REVERSE);
//        capstone.setDirection(Servo.Direction.FORWARD);

        RobotClass masterClass = new RobotClass(this, runtime, FRDrive, FLDrive, BRDrive, BLDrive, lift, capstone, frontGrab, Erectus, foundation, rightGrab, leftGrab);
        waitForStart();
        masterClass.move(2000, 2000, 0.5);

        FLDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FRDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BRDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
}