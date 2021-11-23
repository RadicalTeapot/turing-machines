Engine_TuringEngine : CroneEngine {
    var type = 0;
	var amp = 0.0;
    var freq = 440;
    var pw = 0.5;
    var cutoff = 500;
    var attack = 0.05;
    var release = 1.5;

	*new { arg context, doneCallback;
		^super.new(context, doneCallback);
	}

	alloc {
		SynthDef("TuringEngine", {
            arg out, freq=freq, pw=pw, cutoff=cutoff, attack=attack, release=release, amp=amp, type=type;
            var osc = Select.ar(type, [Pulse.ar(freq, pw), SinOsc.ar(freq), Saw.ar(freq), LFTri.ar(freq)]);
            var filter = MoogFF.ar(osc, cutoff);
            var env = Env.perc(attack, release, amp).kr(2);
            Out.ar(out, Pan2.ar(filter*env, 0));
		}).add;

        this.addCommand("type", "f", {|msg|
            type=msg[1];
        });

        this.addCommand("hz", "f", {|msg|
            var val = msg[1];
            Synth.new("TuringEngine", [
                \out,context.out_b,
                \freq, val, \pw, pw, \cutoff, cutoff, \attack, attack, \release, release, \amp, amp, \type, type
            ],
            target:context.xg);
        });

		this.addCommand("cutoff", "f", {|msg|
			cutoff = msg[1];
		});

        this.addCommand("pw", "f", {|msg|
            pw = msg[1];
        });

		this.addCommand("attack", "f", {|msg|
			attack = msg[1];
		});

		this.addCommand("release", "f", {|msg|
			release = msg[1];
		});

		this.addCommand("amp", "f", {|msg|
			amp = msg[1];
		});
	}
}
