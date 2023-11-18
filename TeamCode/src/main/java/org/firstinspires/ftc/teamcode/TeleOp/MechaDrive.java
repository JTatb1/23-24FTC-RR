package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "MechaDrive", group = "Mecha-Bot")
public class MechaDrive extends LinearOpMode {


    private final MechaHardware robot = new MechaHardware();   // Uses Monster-Bot hardware
    private final ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {

        telemetry.setAutoClear(true);

        telemetry.addData("status", "initialized");
        telemetry.update();


        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        robot.init(hardwareMap);
        double Speedcntrl = .3;
        double Gripper = 1;
        double Tilter = 0;
        double LaunchPos = 0;

        int SLiftPos;
        double SLiftPow = 0;
        double maximum = 6089;
        double minimum = 0;
        double ArmSetter = 500;
        double ArmSetterSetter = 0;

        robot.SLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.SLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            /* //////SpeedControl/////// */
            if (gamepad1.dpad_left) {
                Speedcntrl -= .1;
                sleep(100);
            }
            if (gamepad1.dpad_right) {
                Speedcntrl += .1;
                sleep(100);
            } Speedcntrl = Range.clip(Speedcntrl, .2, .5);


            /*---------------------------Drive Control ---------------------------*/
            double y  = -gamepad1.left_stick_y; //this is reversed
            double x  = gamepad1.left_stick_x * 1.1; // Counteract strafing
            double rx = gamepad1.right_stick_x;


            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double FLdub = (y + x + rx)/ denominator;
            double RLdub = (y - x + rx)/ denominator;
            double FRdub = (y - x - rx)/ denominator;
            double RRdub = (y + x - rx)/ denominator;

            FLdub = FLdub * Speedcntrl;
            RLdub = RLdub * Speedcntrl;
            FRdub = FRdub * Speedcntrl;
            RRdub = RRdub * Speedcntrl;

            robot.FL.setPower(FLdub);
            robot.RL.setPower(RLdub);
            robot.FR.setPower(FRdub);
            robot.RR.setPower(RRdub);

            ////////MotorControl////////

            robot.STilt.setPower(gamepad2.left_stick_x);
            SLiftPos = -robot.SLift.getCurrentPosition();


            if (ArmSetterSetter == 0) {
                if (gamepad2.left_stick_y < 0 && SLiftPos < maximum) {
                    robot.SLift.setPower(gamepad2.left_stick_y);
                } else if (gamepad2.left_stick_y > 0 && SLiftPos > minimum) {
                    robot.SLift.setPower(gamepad2.left_stick_y);
                } else {
                    robot.SLift.setPower(0);
                }
            }



            if (gamepad2.dpad_down){
                ArmSetterSetter = 1;
            }
            if (gamepad2.dpad_up){
                ArmSetterSetter = 0;
            }


            if (ArmSetterSetter == 1){
                SLiftPow = .2;

                if (SLiftPos > ArmSetter){
                    SLiftPow += .01;

                }
                if (SLiftPos < ArmSetter) {
                    SLiftPow -= .01;
                }
                
            }
            robot.SLift.setPower(SLiftPow);



            ///////ServoControl///////

            if (gamepad2.a) { //closes claw
                Gripper = 1;
            }
            if (gamepad2.b) { //opens claw
                Gripper = 0;
            }   robot.Grabber.setPosition(Gripper);


            if (gamepad2.right_stick_y > 0) { //Raises claw
                Tilter += .01;
            }
            if (gamepad2.right_stick_y < 0) { //Lowers claw
                Tilter -= .01;
            }   robot.GrabTilt.setPosition(Tilter);


            if (gamepad2.right_bumper) { //Launch Plane
                LaunchPos = 1;
            }
            if (gamepad2.left_bumper) { //Arm Launcher
                LaunchPos = 0;
            }robot.Launcher.setPosition(LaunchPos);



            /* Send telemetry messages */
            telemetry.addData("Speed Control Set To:", "(%.2f)", Speedcntrl);
            telemetry.addData("Arm At",  "%7d", SLiftPos);
            telemetry.addData("Servos", "Gripper(%.2f), Tilt(%.2f), Launch(%.2f)", Gripper, Tilter, LaunchPos);
            telemetry.addData("FrontDriveMotors", "FL(%.2f), FR(%.2f)", FLdub, FRdub);
            telemetry.addData("RearDriveMotors", "RL(%.2f), RR(%.2f)", RLdub, RRdub);
            telemetry.update();
        }
    }
}

