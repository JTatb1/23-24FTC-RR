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
        int SLiftPos;

        double maximum = 6089;
        double minimum = 0;

        robot.SLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.SLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            ////////SpeedControl///////
            if (gamepad1.dpad_left) {
                Speedcntrl -= .1;
                sleep(100);
            }

            if (gamepad1.dpad_right) {
                Speedcntrl += .1;
                sleep(100);
            }

            if (gamepad1.dpad_down) {
                Speedcntrl = .3;
            }

            Speedcntrl = Range.clip(Speedcntrl, .2, .5);


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

           if(gamepad2.left_stick_y < 0 && -robot.SLift.getCurrentPosition() < maximum) {

                robot.SLift.setPower(gamepad2.left_stick_y);

            }

            else if(gamepad2.left_stick_y > 0 && -robot.SLift.getCurrentPosition() > minimum) {

                robot.SLift.setPower(gamepad2.left_stick_y);

            }

            else {

                robot.SLift.setPower(0);

            }

            ///////ServoControl///////

            if (gamepad2.a) { //clo+ses claw
                Gripper = 1;
            }
            if (gamepad2.x) { //opens claw+
                Gripper = 0;
            }
            robot.Grabber.setPosition(Gripper);


            if (gamepad2.right_stick_x > 0) { //up's claw
                Tilter += .01;
            }
            if (gamepad2.x) { //opens claw+
                Tilter -= .01;
            }
            robot.GrabTilt.setPosition(Tilter);

            if (gamepad2.right_bumper) { //closes claw
                robot.Launcher.setPosition(1);
            }
            if (gamepad2.left_bumper) { //opens claw+
                robot.Launcher.setPosition(0);
            }



            // Send telemetry message to signify robot running;
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Speed Control Set To:", "(%.2f)", Speedcntrl);
            telemetry.addData("DriveMotors", "FL(%.2f), FR(%.2f)", FLdub, FRdub);
            telemetry.addData("DriveMotors", "RL(%.2f), RR(%.2f)", RLdub, RRdub);
            telemetry.addData("Starting at",  "%7d", -robot.SLift.getCurrentPosition());
            telemetry.addData("Servos", "Gripper(%.2f)", Gripper);
            telemetry.update();
        }
    }
}

