For style and script tags display context definition is mandatory.

== Noncompliant Code Example

``
<script>
    var bar='${someText}';
</script>

<style>
    a {
        font-family: '${fontFamily}', sans-serif; 
    }
</style>
``

== Compliant Solution

``
<script>
    var bar='${someText @ context="scriptString"}';
</script>

<style>
    a {
        font-family: '${fontFamily @ context="styleToken"}', sans-serif;
    }
</style>
``
