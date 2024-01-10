package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TeleOp.MechaHardware;

@Autonomous(name = "MechaBasic", group = "Mecha-Bot")
public class Basic extends LinearOpMode {


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
        robot.Grabber.setPosition(Gripper);

        robot.SLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.SLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();

        robot.RL.setPower(.2);
        robot.RR.setPower(.2);
        robot.FL.setPower(.2);
        robot.FR.setPower(.2);

        sleep(2500);

        robot.RL.setPower(0);
        robot.RR.setPower(0);
        robot.FL.setPower(0);
        robot.FR.setPower(0);
        robot.Grabber.setPosition(.5);

        sleep(800);

        robot.RL.setPower(-.1);
        robot.RR.setPower(-.1);
        robot.FL.setPower(-.1);
        robot.FR.setPower(-.1);

        sleep(500);

        robot.RL.setPower(0);
        robot.RR.setPower(0);
        robot.FL.setPower(0);
        robot.FR.setPower(0);

        }
    }



