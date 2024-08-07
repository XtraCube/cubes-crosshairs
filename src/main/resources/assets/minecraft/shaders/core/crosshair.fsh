// Author XtraCube
// https://www.xtracube.dev

#version 150 core

#define PI 3.1415926538
#define TWO_PI 6.28318530718

uniform float GameTime;
uniform vec2 ScreenSize;

uniform vec2 Offset;
uniform float Radius;
uniform float Thickness;
uniform float Alpha;
uniform float Saturation;
uniform float Brightness;
uniform float SpinSpeed;

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
	vec2 st = gl_FragCoord.xy/ScreenSize.y;
    vec2 center = vec2(ScreenSize.x/(2*ScreenSize.y), 0.5) + Offset/ScreenSize;

    float dist = distance(st, center);

    fragColor = vec4(0.0);

    if (dist < Radius + Thickness/2.0 &&
       dist > Radius - Thickness/2.0) {

        vec2 toCenter = center-st;
        float angle = atan(toCenter.y,toCenter.x);

        vec3 color = hsb2rgb(vec3((angle/TWO_PI)+GameTime*2400*SpinSpeed,Saturation,Brightness));

        fragColor = vec4(color, Alpha);
    }

}