// Author XtraCube
// https://www.xtracube.dev

#version 150 core

#define PI 3.1415926538
#define TWO_PI 6.28318530718

// minecraft uniforms
uniform float GameTime;
uniform vec2 ScreenSize;

// movement
uniform vec2 Offset;
uniform float SpinSpeed;
uniform float ProgressOffset;

// size
uniform float Radius;
uniform float Thickness;

// color
uniform float Hue;
uniform float Saturation;
uniform float Brightness;

// transparency
uniform float BaseAlpha;
uniform float Feather;
uniform float Progress;
uniform float DisabledAlpha;


out vec4 fragColor;

//  Function from Iñigo Quiles
//  https://www.shadertoy.com/view/MsS3Wc
vec3 hsb2rgb( in vec3 c ){
    vec3 rgb = clamp(abs(mod(c.x*6.0+vec3(0.0,4.0,2.0),
                             6.0)-3.0)-1.0,
                     0.0,
                     1.0 );
    rgb = rgb*rgb*(3.0-2.0*rgb);
    return c.z * mix(vec3(1.0), rgb, c.y);
}

void main(){
    // normalized coordinate (fit to screen height)
	vec2 st = gl_FragCoord.xy/ScreenSize.y;
    // normalized center coordinate (adjusted for height fit)
    vec2 center = vec2(ScreenSize.x/(2*ScreenSize.y), 0.5) + Offset/ScreenSize;

    // distance to center from coordinate
    float dist = distance(st, center);

    // alpha for feather
    float a_feather = smoothstep(Thickness-Feather, Thickness+Feather, dist-Radius+Thickness)
                     -smoothstep(Thickness-Feather, Thickness+Feather, dist-Radius);

    // calculate angle to center in radians
    vec2 toCenter = center - st;
    float angle = mod(atan(toCenter.y,toCenter.x)+ProgressOffset*PI, TWO_PI);

    // initialize color and progress alpha
    vec3 color = vec3(0.0);
    float a_progress = 1.0;

    // calculate rainbow color based on angle
    // color = hsb2rgb( vec3(( angle/TWO_PI ) + GameTime * 2400 * SpinSpeed, Saturation, Brightness ));

    // normal color calculation
    color = hsb2rgb(vec3(Hue, Saturation, Brightness));

    // to account for smoothstep losing a couple pixels
    float pre_progress = Progress + Progress*Feather;

    // calculate alpha using progress
    a_progress = clamp( DisabledAlpha + smoothstep( pre_progress, pre_progress-Feather, angle/TWO_PI ), 0.0, 1.0);

    // final fragColor calculation, multiplying various alphas
    fragColor = vec4(color, BaseAlpha * a_feather * a_progress);
}