#version 150 core

// Author XtraCube
// https://www.xtracube.dev

#define PI 3.1415926538
#define TWO_PI 6.28318530718

// minecraft uniforms
uniform float GameTime;
uniform vec2 ScreenSize;

// movement
uniform vec2 Offset;
uniform float SpinSpeed;
uniform float CooldownOffset;

// size and shape
uniform int Shape;
uniform float Radius;
uniform float Thickness;

// color
uniform float Hue;
uniform float Saturation;
uniform float Brightness;

// transparency
uniform float BaseAlpha;
uniform float Feather;
uniform float Cooldown;
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

// return alpha for circle shape
float circle(vec2 st, vec2 center) {
    float dist = distance(st, center);

    return smoothstep(Radius-Feather, Radius+Feather, dist+Thickness/2.)-
    smoothstep(Radius-Feather, Radius+Feather, dist-Thickness/2.);
}

// return alpha for square shape
float rect(vec2 st, vec2 center)
{
    vec2 bl = step(vec2(.5-Radius-Thickness/2.)+center,st);       // bottom-left
    vec2 tr = step(vec2(.5-Radius-Thickness/2.)-center,1.0-st);   // top-right

    float outer = bl.x * bl.y * tr.x * tr.y;

    bl = step(vec2(.5-Radius+Thickness/2.)+center,st);       // bottom-left
    tr = step(vec2(.5-Radius+Thickness/2.)-center,1.0-st);   // top-right

    float inner = bl.x * bl.y * tr.x * tr.y;

    return outer-inner;
}

float progress(vec2 st, vec2 center){
    // to account for smoothstep losing a couple pixels
    float pre_progress = Cooldown + Cooldown*Feather;

    // calculate angle to center in radians
    vec2 toCenter = center - st;
    float angle = mod(atan(toCenter.y,toCenter.x)+CooldownOffset*PI, TWO_PI);


    return clamp( DisabledAlpha + smoothstep( pre_progress, pre_progress-Feather, angle/TWO_PI ), 0.0, 1.0);
}

void main(){
    // normalized coordinate (fit to screen height)
    vec2 st = gl_FragCoord.xy/ScreenSize.y;

    // calculate center even when resolution isnt square
    vec2 center = vec2(ScreenSize.x/(2.*ScreenSize.y), 0.5) + Offset/ScreenSize;

    // shape function
    float shape;

    if (Shape < 1) {
        shape = circle(st, center);
    }
    else {
        shape = rect(st, center-vec2(0.5));
    }

    float alpha = BaseAlpha * shape * progress(st, center);

    // HSB color calculation
    vec3 color = hsb2rgb(vec3(Hue, Saturation, Brightness));

    // final fragColor calculation, multiplying various alphas
    fragColor = vec4(color, alpha);
}