<template>
  <div class="hello">

      <!-- better for mobile version -->
      <div v-if="isMobile()">
          <h1 style="font-size: 8vw">{{ msg }}</h1>
      </div>

      <!-- better for Desktop version -->
      <div v-else>
          <h1>{{ msg }}</h1>
      </div>

      <p>

      </p>
  </div>
</template>

<script>
    export default {
        name: 'HelloWorld',
        props: {
            msg: String
        },
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            }
        }

    }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h3 {
        margin: 40px 0 0;
    }
    ul {
        list-style-type: none;
        padding: 0;
    }
    li {
        display: inline-block;
        margin: 0 10px;
    }
    a {
        color: #42b983;
    }

    .hello {
        padding: 50px;
    }
</style>
