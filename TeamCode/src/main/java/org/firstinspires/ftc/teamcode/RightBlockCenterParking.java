package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Right Block Center Parking", group="Linear Opmode")
public class RightBlockCenterParking extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FRDrive = null;
    private DcMotor FLDrive = null;
    private DcMotor BRDrive = null;
    private DcMotor BLDrive = null;
    private DcMotor Lift = null;
    private Servo Erectus = null;
    private Servo frontGrab = null;
    private Servo foundation = null;
    double timeout = 5;
    int FLPosition = 0;
    int FRPosition = 0;
    int BLPosition = 0;
    int BRPosition = 0;

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FRDrive  = hardwareMap.get(DcMotor.class, "front_right");
        FLDrive = hardwareMap.get(DcMotor.class, "front_left");
        BRDrive  = hardwareMap.get(DcMotor.class, "back_right");
        BLDrive  = hardwareMap.get(DcMotor.class, "back_left");
        Lift  = hardwareMap.get(DcMotor.class, "lift");
        Erectus = hardwareMap.get(Servo.class, "erectus");
        frontGrab = hardwareMap.get(Servo.class, "front_grab");
        foundation = hardwareMap.get(Servo.class, "foundation");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery

        FRDrive.setDirection(DcMotor.Direction.REVERSE);
        FLDrive.setDirection(DcMotor.Direction.FORWARD);
        BRDrive.setDirection(DcMotor.Direction.REVERSE);
        BLDrive.setDirection(DcMotor.Direction.FORWARD);
        Lift.setDirection(DcMotor.Direction.REVERSE);
        Erectus.setDirection(Servo.Direction.FORWARD);
        frontGrab.setDirection(Servo.Direction.FORWARD);
        foundation.setDirection(Servo.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        frontGrab.setPosition(0.85);
        sleep(500);
        Erectus.setPosition(0.5);
        foundation.setPosition(0.45);

        telemetry.addData("Position", "FR: (%.2f) FL: (%.2f) BR: (%.2f) BL: (%.2f)", (float)FRDrive.getCurrentPosition(), (float)FLDrive.getCurrentPosition(), (float)BRDrive.getCurrentPosition(), (float)BLDrive.getCurrentPosition());
        telemetry.update();

        FLDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FRDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BRDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Stage 1", true); //drive up to blocks
        moverobot(1700,1700,0.3);
        sleep(500);

        frontGrab.setPosition(0);
        sleep(500);

        telemetry.addData("Stage 2", true); //back up slightly
        moverobot(-200,-200,0.3);
        sleep(500);

        telemetry.addData("Stage 3", true); //rotate left
        moverobot(-550,550,0.3);
        sleep(500);

        telemetry.addData("Stage 4", true); //drive past bridge
        moverobot(2500,2500,0.3);
        sleep(500);

        frontGrab.setPosition(0);
        sleep(500);

        telemetry.addData("Stage 5", true); //backup and park
        moverobot(-1300,-1300,0.3);
        sleep(500);
    }

    private void moverobot(int left, int right, double power){
        if(opModeIsActive()){
            FLPosition += left;
            FRPosition += right;
            BLPosition += left;
            BRPosition += right;
            FLDrive.setTargetPosition(FLPosition);
            FRDrive.setTargetPosition(FRPosition);
            BLDrive.setTargetPosition(BLPosition);
            BRDrive.setTargetPosition(BRPosition);

            FLDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FRDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BLDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BRDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            FLDrive.setTargetPosition(FLPosition);
            FRDrive.setTargetPosition(FRPosition);
            BLDrive.setTargetPosition(BLPosition);
            BRDrive.setTargetPosition(BRPosition);

            runtime.reset();

            while(FRDrive.getPower() != power || FLDrive.getPower() != power || BLDrive.getPower() != power || BRDrive.getPower() != power){
                FLDrive.setPower(power);
                FRDrive.setPower(power);
                BLDrive.setPower(power);
                BRDrive.setPower(power);
            }

            while (opModeIsActive() && (runtime.seconds() < timeout) && (FLDrive.isBusy() && FRDrive.isBusy() && BLDrive.isBusy() && BRDrive.isBusy())) {
                telemetry.addData("Position", "FR: (%.2f) FL: (%.2f) BR: (%.2f) BL: (%.2f)", (float)FRDrive.getCurrentPosition(), (float)FLDrive.getCurrentPosition(), (float)BRDrive.getCurrentPosition(), (float)BLDrive.getCurrentPosition());
                telemetry.addData("Target Position", "FR: (%.2f) FL: (%.2f) BR: (%.2f) BL: (%.2f)", (float)FRDrive.getTargetPosition(), (float)FLDrive.getTargetPosition(), (float)BRDrive.getTargetPosition(), (float)BLDrive.getTargetPosition());
                telemetry.addData("Power", "FR: (%.2f) FL: (%.2f) BR: (%.2f) BL: (%.2f)", (float)FRDrive.getPower(), (float)FLDrive.getPower(), (float)BRDrive.getPower(), (float)BLDrive.getPower());
                telemetry.update();
            }

            FRDrive.setPower(0);
            FLDrive.setPower(0);
            BLDrive.setPower(0);
            BRDrive.setPower(0);

            FLDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FRDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BLDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BRDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}