# RoundTopLayout_Demo
A Layout with a round top on top

It looks like this

![image](https://github.com/matchparent/RoundTopLayout_Demo/blob/master/screenshot/rtl_screenshot.png)

There's five attribute you may use

* backcolor - the backcolor of the layout,don't use android:background.(color)

* centerwidth - relative width of the arc.(dp)

* gapsize - distance from the top of the arc to the line.(dp)

* linecolor - color of the line.(color)

* linesize - stroke width for the lines.(dp)

When I was working on this widget,I draw an arc and made it intersect with two lines on the left and right,then i draw a rect(backcolor) to cover the unnecessary part of the arc(for the absolute centerwidth is hard to calculate,so i make it a relative one,when you are using this you can try values to see if it match).
